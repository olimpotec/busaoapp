<?
/*
 * www.fundect.ms.gov.br
 * Arquivo de Configuracoes     
 */

$BANCO = array ();
$FUNDECT = array ();
              
// Banco de Dados 
$BANCO ["host"] 			= "localhost";
$BANCO ["user"] 			= "fundect";
$BANCO ["pass"] 			= "sua_senha"; 
$BANCO ["schema"]			= "seu_esquema";
$BANCO ["database"]			= "sigfundect";

$_ANALYTICS = 'UA-1263715';

$FUNDECT ["fotopesq"]		= "arquivos/ftp/";
$FUNDECT ["arqadmin"]		= "arquivos/adm/";
$FUNDECT ["arqprojetos"]	= "arquivos/prj/";
$FUNDECT ["arqcontratados"]	= "arquivos/ctr/";
$FUNDECT ["anexocontratados"]   = "arquivos/anx_ctr/";
$FUNDECT ["anexomsg"]		= "arquivos/anx/";
$FUNDECT ["form_xml"]		= "arquivos/xml/";
$FUNDECT ["arqfolhas"] 		= "arquivos/pgt/";
$FUNDECT ["txtProdam"]      	= "arquivos/prd/";
$FUNDECT ["debug"]		= "arquivos/dbg/";
$FUNDECT ["log"]		= "arquivos/log/log.db";
$FUNDECT ["modelo_anx"]         = "arquivos/mod_anx/";
$FUNDECT ["arqconvenio"]        = "arquivos/atj/";
$FUNDECT ["anexoprogramas"]     = "arquivos/apg/";
$FUNDECT ["backup"]		= "arquivos/bkp/";
$FUNDECT ["anexorede"]          = "arquivos/rds/";
$FUNDECT["doc_pessoais"] 	= "arquivos/dpes/";

$CONTROLE ["INATIVIDADE"] 	= 30 * 60;  //tempo em segundos
$CONTROLE ["SECRET_HASH"] 	= "5adb8bf637731beae37b79c27f48a47e";
$CONTROLE ["INATIVIDADE_TEMPO_EXTRA"] = 5; //tempo em segundos, para margem de erro entre navegador e servidor
$CONTROLE ["INATIVIDADE_DESTRUIR_SESSAO"] = 30 * 60; // tempo para destruir a sessao depois de bloqueada, 0 - nгdestruir a sessao

// SIGAdHoc

$SIGADOC ['PRIVATE'] = 'vxrEbiKh9Bj2wlTzQvssiVwHizhVqm1V2';
$SIGADOC ['PUBLIC'] = 'irDrmaEP56QQ2SV2R2vBSBMs5pm2zDR452';
$SIGADOC ['TOKEN'] = 'kaNXUvdtVr1LV141Y6sJQ7bztCTXahPu32';
$SIGADOC ['TIMEZONE'] = 'America/Campo_Grande';
$SIGADOC ['IP'] = '200.129.205.12';

// Curriculo CNPq/Lattes
$CURRICULO ["lattes"] 		= "http://genos.cnpq.br:12010/dwlattes/owa/prc_imp_cv_int?f_cod=";

// Dados Fundect
$FUNDECT ["email"] 			= "dcientifica@fundect.ms.gov.br";
$FUNDECT ["url"]			= "http://sigfundect.ledes.net/";
$FUNDECT ["aviso"]			= "noticias@fundect.ms.gov.br";
$FUNDECT ["reply"]			= "noticias@fundect.ms.gov.br";
$FUNDECT ["apoiados"]		= 30;
$FUNDECT ["cidade_id"] 		= "7385";
//Opcoes de layout do sistema 
// (basta citar o nome do layout que deseja carregar (nome da pasta), verifique as pastas de layout e layout_admin da raiz e administracao)
//pode ser atualmente "fapespa" ou "fundect"

$FUNDECT ["lay_principal"] = "fundect";
$FUNDECT ["lay_admin"] = "fundect";

$diretorioLayout = 'layouts_index/'.$FUNDECT ["lay_principal"];
$diretorioLayoutAdmin = 'layouts_admin/'.$FUNDECT ["lay_principal"];

//. opcoes de configuracao para o sistema principalmente para os relatorios de prestacao de contas
//; As variбveis abaixo devem ser configuradas de acordo com a Fundaзгo

$FUNDECT ["nome_fundacao"]  = "FUNDECT";
$FUNDECT ["nome_fundacao_completo"]  = "Fundaзгo de Apoio ao Desenvolvimento do Ensino, Ciкncia e Tecnologia do Estado de Mato Grosso do Sul";

$FUNDECT ["nome_secretaria"]  = "SEMAC";
$FUNDECT ["nome_estado"]  = "DE MATO GROSSO DO SUL"; //; no caso da FUNDECT deveria ser "DE MATO GROSSO DO SUL"
$FUNDECT ["nome_diretor_presidente"] = "Prof. Dr. Marcelo Augusto Santos Turine"; //; caso mude o diretor_presidente й sу mudar aqui
$FUNDECT ["secretario_estado"] = "Carlos Alberto Negreiros Said de Menezes"; //; Secretбrio de Estado de Desenvolvimento, Ciкncia e Tecnologia.
$FUNDECT ["nome_cidade"]  = "Campo Grande"; //; referente a cidade onde a Fundaзгo estб localizada (usada para a geraзгo dos termos).
$FUNDECT ["nome_cidade_estado"] = "Campo Grande - MS";
$FUNDECT ["endereco_fundacao"] = "Rua Sгo Paulo, 1436 - Bairro Vila Cйlia";
$FUNDECT ["cep_fundacao"] = "79.010-050";
$FUNDECT ["telefone_fundacao"] = "(67) 3316-6700";
$FUNDECT ['concedente'] = "OUTORGANTE"; //;  essa variбvel deve ser setada de acordo com o termo utilizado na Fundaзгo "CONCEDENTE ou OUTORGANTE".

$FUNDECT ["sigla_estado"] = "MS";
$FUNDECT ["cidade_id"] = "7385";

$FUNDECT["background_menuBar"]   = "red";
$FUNDECT["background_menuOn"]   = "#4368B4";
$FUNDECT["background_menuOver"] = "#77A3EF";

//. configuracao para a geracao do protocolo de acordo com cada instituicao
//. que pode ser "fapespa" (Ex.UNI0108250)ou "fundect" (Ex.880.19.105.01072008)
$FUNDECT['tipo_protocolo'] = "fundect";

//.configuracao de envio de email do sistema via smtp autenticado na funcao mail_to de funcoes.php
$FUNDECT['smtp'] = FALSE;
$FUNDECT['smtp_host'] = '';
$FUNDECT['smtp_email_from'] = 'dcientifica@fundect.ms.gov.br';
$FUNDECT['smtp_usuario_nome'] = 'SIGFUNDECT';
$FUNDECT['smtp_usuario_login'] = 'sigfundect';
$FUNDECT['smtp_senha'] = '';
$FUNDECT['smtp_email_reply'] = 'no-reply@fundect.ms.gov.br';
$FUNDECT['smtp_email_reply_nome'] = 'No Reply';

$FUNDECT["tamanho_max_upload"] = 10485760; // Em bytes (Equivalente a 10Mb)
$FUNDECT["tamanho_max_upload_str"] = "10 MBytes"; // Legenda para labels de tamanho

// Arquivo para carregamento dos componentes necessarios 
// em todo o sistema. deve ser incluido depois 
// das declaracoes acima. (glandre - 02/05/2013)
require_once 'bootstrap.php';
