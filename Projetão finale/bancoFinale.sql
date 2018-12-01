create table Sala(
codigo int primary key,
nome varchar(50) not null,
capacidade int not null,
qtdAtual int not null
)

create table SalaUsuario(
codigoSala int not null,
constraint fkcodSala foreign key (codigoSala) references Sala(codigo),
nomeUsuario varchar(30) not null
)

select * from Sala

select * from SalaUsuario

create proc insereNaSala
@codigoSala int = null,
@nome varchar(30) = null
as
if(@codigoSala is null or @nome is null)
  print('Passe os parâmetros corretamente.')
  else
      Begin
           if((select qtdAtual from Sala where codigo = @codigoSala) < (select capacidade from Sala where codigo = @codigoSala))
		      begin
			      update Sala set qtdAtual = (qtdAtual + 1) where codigo = @codigoSala
				  update SalaUsuario set codigoSala = @codigoSala where nomeUsuario = @nome
		      end
      end

create proc tiraDaSala
@codigoSala int = null,
@nomeUsuario varchar(30) = null
as
if(@codigoSala is null or @nomeUsuario is null)
  print('Passe os parâmetros corretamente.')
  else
       Begin
	        if((select nomeUsuario from SalaUsuario where @codigoSala = codigoSala) = @nomeUsuario)
			   begin
			         update Sala set qtdAtual = (qtdAtual - 1) where codigo = @codigoSala
					 delete from SalaUsuario where codigoSala = @codigoSala and nomeUsuario = @nomeUsuario
			   end
		end