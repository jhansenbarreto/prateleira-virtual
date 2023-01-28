package com.prateleiravirtual.domain.service;

import static com.prateleiravirtual.domain.exception.ErroRegraNegocioException.ErroRegraNegocioMessages.*;

import com.prateleiravirtual.domain.exception.UsuarioNaoEncontradoException;
import com.prateleiravirtual.domain.exception.ErroRegraNegocioException;
import com.prateleiravirtual.domain.model.AcaoHistorico;
import com.prateleiravirtual.domain.model.AtivacaoUsuario;
import com.prateleiravirtual.domain.model.ImagemDetalhes;
import com.prateleiravirtual.domain.model.ImagemUsuario;
import com.prateleiravirtual.domain.model.RedefinicaoSenha;
import com.prateleiravirtual.domain.model.Usuario;
import com.prateleiravirtual.domain.repository.UsuarioRepository;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviços relacionados à classe Usuario e suas classes agregadas.
 *
 * @author Jhansen Barreto
 */
@Service
public class UsuarioService {

    /*  IMPORTANTE:
    
        O método flush() do repositório é usado em diversos lugares dessa classe
        pq após a implementação do método detach(), tbm do repositório, os objetos
        do tipo Usuario deixaram de ter suas atualizações commitadas no banco de dados
        automaticamente, de acordo às mudanças durante a existência da instância no 
        contexto de persistência do JPA. Ou seja, para o JPA fazer o commit, agora é 
        obrigatório pedir que ele descarregue sua fila. Para perceber a diferença 
        basta observar o código de outras classes @Service.
    
        OBS: Ainda não descobri o motivo da classe inteira ser afetada e não 
        apenas o método que usa o detach().
     */
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ImagemStorageService storageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HistoricoUsuarioService historicoService;

    /**
     * Método para listar todos os Usuario salvos.
     *
     * @return -> Lista de Usuario
     */
    public List<Usuario> listar() {
        return repository.findAll();
    }

    /**
     * Método para buscar um único Usuario. Caso não seja encontrado, uma
     * exceção é lançada.
     *
     * @param id (Identificador do Usuario)
     * @return -> O Usuario do ID informado, se existir.
     */
    public Usuario buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    /**
     * Método para pesquisar Usuario por username. Username é um atributo único.
     * Este método retorna uma lista pois a busca pode ser por parte de uma
     * palavra, até mesmo uma letra.
     *
     * @param username (Palavra completa ou parcial de um nome de usuário)
     * @return -> Lista de Usuario que contém parte ou todo o username
     */
    public List<Usuario> buscarPorUsername(String username) {
        return repository.findByUsernameContaining(username);
    }

    /**
     * Método para buscar um Usuario por e-mail. Caso não seja encontrado, uma
     * exceção é lançada.
     *
     * @param email (E-mail de um Usuario)
     * @return -> O Usuario do e-mail informado, se existir.
     */
    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email).orElseThrow(()
                -> new UsuarioNaoEncontradoException("E-mail não cadastrado."));
    }

    /**
     * Método para salvar um Usuario novo ou alterações de uma atualização. Caso
     * seja um novo Usuario, é obrigatório informar o codigoAtivacao. Sendo uma
     * atualização, o valor para este parâmetro deve ser 'null'.
     *
     * @param usuario (Usuario a ser salvo)
     * @param codigoAtivacao (Código para uma ativação futura)
     * @return -> Usuario salvo
     */
    @Transactional
    public Usuario salvar(Usuario usuario, String codigoAtivacao) {
        evitarUsuarioDuplicado(usuario);
        usuario.setSenha(encrypt(usuario.getSenha()));
        repository.save(usuario);
        repository.flush();

        if (codigoAtivacao != null) {
            salvarAtivacaoUsuario(usuario, codigoAtivacao);
        }
        return usuario;
    }

    /**
     * Método responsável por deletar um Usuario. Caso não exista o registro
     * desejado, uma exceção é lançada.
     *
     * @param id (Identificador do usuário a ser deletado)
     */
    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new UsuarioNaoEncontradoException(id);
        }
    }

    /**
     * Método para buscar o objeto que contém o código para ativação de um
     * usuário. Este objeto é mapeado com o mesmo ID do Usuario, mantendo sempre
     * uma relação de Um pra Um. Caso não seja encontrado, uma exceção é
     * lançada.
     *
     * @param usuarioId (Identificador do Usuario)
     * @return -> AtivacaoUsuario
     */
    private AtivacaoUsuario buscarAtivacaoPorId(Long usuarioId) {
        return repository.findAtivacaoById(usuarioId).orElseThrow(()
                -> new ErroRegraNegocioException(CODIGO_INEXISTENTE));
    }

    /**
     * Método para buscar o objeto que contém o código para redefinição de senha
     * esquecida de um usuário. Este objeto é mapeado com o mesmo ID do Usuario,
     * mantendo sempre uma relação de Um pra Um. Caso não seja encontrado, uma
     * exceção é lançada.
     *
     * @param usuarioId (Identificador do Usuario)
     * @return -> RedefinicaoSenha
     */
    private RedefinicaoSenha buscarRedefinicaoPorId(Long usuarioId) {
        return repository.findRedefinicaoById(usuarioId).orElseThrow(()
                -> new ErroRegraNegocioException(CODIGO_INEXISTENTE));
    }

    /**
     * Método para salvar um código de ativação para um usuário que será ativado
     * futuramente. O usuário recebe o código por e-mail.
     *
     * @param usuario (Usuario que terá o código de ativação agregado)
     * @param codigo (Código de ativação)
     */
    @Transactional
    public void salvarAtivacaoUsuario(Usuario usuario, String codigo) {
        if (usuario.getAtivo()) {
            throw new ErroRegraNegocioException(USUARIO_ATIVADO);
        }
        AtivacaoUsuario ativacao;

        try {
            //SE JÁ EXISTIR UM CÓDIGO ELE É ATUALIZADO
            ativacao = buscarAtivacaoPorId(usuario.getId());
            ativacao.setCodigo(encrypt(codigo));

        } catch (ErroRegraNegocioException ex) {
            //CASO CONTRÁRIO UM NOVO É CRIADO
            ativacao = new AtivacaoUsuario(encrypt(codigo), usuario);
        }
        repository.save(ativacao);
        repository.flush();
        enviarEmail(
                usuario,
                EmailDetails.ATIVAR_CONTA,
                codigo,
                convertOffsetToDate(ativacao.getDataAtualizacao())
        );
    }

    /**
     * Método para ativar um Usuario.
     *
     * @param id (Identificador do Usuario a ser ativado)
     * @param codigo (Código de ativação)
     */
    @Transactional
    public void ativarUsuario(Long id, String codigo) {
        var ativacao = buscarAtivacaoPorId(id);

        if (!verify(codigo, ativacao.getCodigo())) {
            throw new ErroRegraNegocioException(CODIGO_INEXISTENTE);
        }

        // A validade deve ser contabilizada à partir da data de atualização,
        // pois um novo código pode ser emitido, atualizando o antigo.
        if (conferirValidade(ativacao.getDataAtualizacao())) {
            ativacao.getUsuario().setAtivo(Boolean.TRUE);
            repository.flush();

            historicoService.salvar(AcaoHistorico.ATIVAR, ativacao.getUsuario());
            repository.deleteAtivacao(ativacao.getId());
            repository.flush();
        }
    }

    /**
     * Método para desativar um Usuario.
     *
     * @param usuario (Usuario a ser desativado)
     */
    @Transactional
    public void desativarUsuario(Usuario usuario) {
        usuario.setAtivo(Boolean.FALSE);
        historicoService.salvar(AcaoHistorico.DESATIVAR, usuario);
        repository.flush();
    }

    /**
     * Método para salvar o desejo de redefinir uma senha que foi esquecida. No
     * fim da execução, o usuário recebe um e-mail com o código de redefinição.
     *
     * @param usuario (Objeto do tipo Usuario)
     * @param codigo (Código de redefinição)
     */
    @Transactional
    public void salvarRedefinicaoSenha(Usuario usuario, String codigo) {
        RedefinicaoSenha redefinicao;
        try {
            //SE JÁ EXISTIR UM CÓDIGO ELE É ATUALIZADO
            redefinicao = buscarRedefinicaoPorId(usuario.getId());
            redefinicao.setCodigo(encrypt(codigo));

        } catch (ErroRegraNegocioException ex) {
            //CASO CONTRÁRIO UM NOVO É CRIADO
            redefinicao = new RedefinicaoSenha(encrypt(codigo), usuario);
        }
        repository.save(redefinicao);
        repository.flush();
        enviarEmail(
                usuario,
                EmailDetails.REDEFINIR_SENHA,
                codigo,
                convertOffsetToDate(redefinicao.getDataAtualizacao())
        );
    }

    /**
     * Método para redefinir uma senha que foi esquecida.
     *
     * @param id (Identificador do usuário)
     * @param codigo (Código de redefinição)
     * @param novaSenha (Nova senha)
     */
    @Transactional
    public void redefinirSenhaEsquecida(Long id, String codigo, String novaSenha) {
        var redefinicao = buscarRedefinicaoPorId(id);

        if (!verify(codigo, redefinicao.getCodigo())) {
            throw new ErroRegraNegocioException(CODIGO_INEXISTENTE);
        }

        // A validade deve ser contabilizada à partir da data de atualização,
        // pois um novo código pode ser emitido, atualizando o antigo.
        if (conferirValidade(redefinicao.getDataAtualizacao())) {
            alterarSenha(redefinicao.getUsuario(), null, novaSenha);
            repository.deleteRedefinicao(redefinicao.getId());
            repository.flush();
        }
    }

    /**
     * Método para alteração de senha. Caso for uma alteração de uma senha que
     * foi esquecida, é necessário passar o valor 'null' no parâmetro "atual",
     * caso contrário, passe o valor da última senha salva.
     *
     * @param usuario (Usuario a ter a senha alterada)
     * @param atual (Senha anterior à nova senha)
     * @param nova (Nova senha)
     */
    @Transactional
    public void alterarSenha(Usuario usuario, String atual, String nova) {
        if ((atual != null) && (!verify(atual, usuario.getSenha()))) {
            throw new ErroRegraNegocioException(SENHA_ATUAL_INCORRETA);
        }
        usuario.setSenha(encrypt(nova));
        repository.flush();
    }

    /**
     * Método responsável por salvar imagem de um Usuario. Salva o objeto no
     * banco de dados e o arquivo da imagem no disco local ou na nuvem,
     * dependendo da escolha feita no 'application.properties'
     *
     * @param usuario (Usuario associada a imagem)
     * @param detalhes (Objeto ImagemDetalhes da imagem do Usuario)
     * @param inputStream (Stream do arquivo de imagem)
     */
    @Transactional
    public void salvarImagem(Usuario usuario, ImagemDetalhes detalhes, InputStream inputStream) {
        String nomeAnterior = null;

        //Se o usuário já possuir uma imagem, ocorre a troca pela nova imagem
        if (usuario.getImagem() != null) {
            nomeAnterior = usuario.getImagem().getDetalhes().getNomeArquivo();
            usuario.getImagem().setDetalhes(detalhes);
        } else {
            var imagem = new ImagemUsuario();
            imagem.setId(usuario.getId());
            imagem.setDetalhes(detalhes);
            usuario.setImagem(imagem);
        }
        repository.flush();

        //ADICIONANDO URL DE ACESSO ATRIBUÍDA À IMAGEM SALVA (DISCO OU NUVEM)
        usuario.getImagem().getDetalhes().setUrl(
                storageService.atualizar(
                        usuario.getImagem().getDetalhes().getNomeArquivo(),
                        inputStream,
                        nomeAnterior
                ));
        repository.flush();
    }

    /**
     * Método responsável por buscar uma determinada imagem, servindo o stream
     * do arquivo para um possível download.
     *
     * @param nomeArquivo (Nome do arquivo)
     * @return -> Stream do arquivo informado
     */
    public InputStream buscarImagem(String nomeArquivo) {
        return storageService.download(nomeArquivo);
    }

    /**
     * Método para deletar imagem de um Usuario. Remove não só do banco de dados
     * mas também apaga o arquivo onde estiver armazenado.
     *
     * @param usuario (Usuario com Imagem associada)
     */
    @Transactional
    public void excluirImagem(Usuario usuario) {
        var nomeArquivo = usuario.getImagem().getDetalhes().getNomeArquivo();

        usuario.setImagem(null);
        repository.flush();

        repository.deleteImagem(usuario.getId());
        repository.flush();

        storageService.remover(nomeArquivo);
    }

    /**
     * Método para evitar o cadastro duplicado de um usuário, já que username e
     * e-mail são únicos. Caso exista algum dos campos mencionados já salvo no
     * banco, lança exceção.
     *
     * @param usuario (Usuario a ser verificado)
     */
    private void evitarUsuarioDuplicado(Usuario usuario) {
        repository.detach(usuario);

        var email = usuario.getEmail();
        var username = usuario.getUsername();

        var users = repository.findByEmailOrUsername(email, username);
        if ((users != null) && (!users.isEmpty())) {

            //O FOR NÃO PREJUDICA A PERFORMANCE DO CÓDIGO, A LISTA SEMPRE TERÁ DE 0 A 2 ITENS NO MÁXIMO
            users.forEach(item -> {
                var equals = item.equals(usuario);
                if ((!equals) && (item.getUsername().equals(username)) && (item.getEmail().equals(email))) {
                    throw new ErroRegraNegocioException(USERNAME_EMAIL_CADASTRADOS);

                } else if ((!equals) && item.getEmail().equals(email)) {
                    throw new ErroRegraNegocioException(EMAIL_CADASTRADO);

                } else if ((!equals) && item.getUsername().equals(username)) {
                    throw new ErroRegraNegocioException(USERNAME_CADASTRADO);
                }
            });
        }
    }

    /**
     * Método para encriptação de Strings.
     *
     * @param original (String original)
     * @return -> String criptografada
     */
    private String encrypt(String original) {
        return BCrypt.hashpw(original, BCrypt.gensalt(12));
    }

    /**
     * Método para comparar uma determinada String "aberta" com outra já
     * criptografada.
     *
     * @param plaintext (String a ser verificada)
     * @param encrypted (String criptografada)
     * @return -> true ou false
     */
    private boolean verify(String plaintext, String encrypted) {
        return BCrypt.checkpw(plaintext, encrypted);
    }

    /**
     * Método para envio de e-mails de ativação de usuário ou redefinição de
     * senha. Tipo de e-mail definido de acordo com o parâmetro "detalhes".
     *
     * @param usuario (objeto do tipo Usuario)
     * @param detalhes (Enum utilizado para escolher qual tipo de e-mail enviar)
     * @param codigo (Código de ativação ou redefinição)
     */
    private void enviarEmail(Usuario usuario, EmailDetails detalhes, String codigo, Date emissao) {
        var email = EmailService.Email.builder()
                .assunto("Prateleira Virtual - ".concat(detalhes.getSubject()))
                .destinatario(usuario.getEmail())
                .corpo(detalhes.getNameFileHTML())
                .variavel("username", usuario.getUsername())
                .variavel("codigo", codigo)
                .variavel("emissao", emissao)
                .variavel("email", usuario.getEmail())
                .build();
        emailService.enviar(email);
    }

    /**
     * Este método confere se uma determinada data/hora está dentro dos últimos
     * 15 minutos. Utilizado para conferir a validade de códigos de ativação de
     * usuário e códigos de redefinição de senha. Se a validade estiver vencida
     * uma exceção é lançada.
     *
     * @param date (Data a ser verificada)
     * @return -> true se finalizar o método
     */
    private boolean conferirValidade(OffsetDateTime date) {
        var calendar = Calendar.getInstance();
        calendar.setTime(Date.from(date.toInstant()));
        calendar.add(Calendar.MINUTE, 15);

        if (new Date().after(calendar.getTime())) {
            throw new ErroRegraNegocioException(CODIGO_EXPIRADO);
        }
        return true;
    }

    /**
     * Método para converter objetos OffsetDateTime em Date.
     *
     * @param date (Objeto do tipo OffsetDateTime)
     * @return -> Objeto convertido para o tipo Date
     */
    private Date convertOffsetToDate(OffsetDateTime date) {
        return Date.from(date.toInstant());
    }

    /**
     * Enumeração criada para minimizar a customização do envio de e-mails, onde
     * apenas o "tipo" de e-mail é necessário para servir as outras informações
     * necessárias já definidas por padrão.
     */
    @Getter
    private enum EmailDetails {

        ATIVAR_CONTA("ativacao-conta.html", "Ativar Conta"),
        REDEFINIR_SENHA("redefinicao-senha.html", "Redefinir Senha");

        private final String nameFileHTML;
        private final String subject;

        EmailDetails(String nomeArquivo, String assunto) {
            this.nameFileHTML = nomeArquivo;
            this.subject = assunto;
        }
    }
}
