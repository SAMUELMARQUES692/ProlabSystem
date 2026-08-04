package prolab.system.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import prolab.system.entity.Cliente;
import prolab.system.request.ClienteRequest;
import prolab.system.response.ClienteResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClienteMapperTest {

    private final ClienteMapper mapper = Mappers.getMapper(ClienteMapper.class);

    @Test
    void toCliente() {

        ClienteRequest request = ClienteRequest.builder()
                .razaoSocial("Empresa Teste")
                .cnpj("12345678910123")
                .contato("numero Teste")
                .endereco("Rua Teste, 123, Bairro Teste, Cidade Teste, Estado Teste")
                .build();

        Cliente cliente = mapper.toCliente(request);

        assertNotNull(cliente);

        assertEquals(request.cnpj(), cliente.getCnpj());
        assertEquals(request.razaoSocial(), cliente.getRazaoSocial());
        assertEquals(request.contato(), cliente.getContato());
        assertEquals(request.endereco(), cliente.getEndereco());

    }

    @Test
    void toClienteResponse() {
        Cliente cliente = Cliente.builder()
                .id(1L)
                .razaoSocial("Empresa Teste")
                .cnpj("12345678910123")
                .contato("numero Teste")
                .endereco("Rua Teste, 123, Bairro Teste, Cidade Teste, Estado Teste")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        ClienteResponse response = mapper.toClienteResponse(cliente);

        assertNotNull(response);

        assertEquals(cliente.getId(), response.id());
        assertEquals(cliente.getRazaoSocial(),response.razaoSocial());
        assertEquals(cliente.getCnpj(), response.cnpj());
        assertEquals(cliente.getContato(), response.contato());
        assertEquals(cliente.getEndereco(), response.endereco());
        assertEquals(cliente.getAtivo(), response.ativo());
        assertEquals(cliente.getCreatedAt(), response.createdAt());
    }

    @Test
    void atualizarCliente() {
        ClienteRequest request = ClienteRequest.builder()
                .razaoSocial("Empresa Teste")
                .cnpj("12345678910123")
                .contato("numero Teste")
                .endereco("Rua Teste, 123, Bairro Teste, Cidade Teste, Estado Teste")
                .build();

        Cliente cliente = Cliente.builder()
                .id(1L)
                .razaoSocial("Empresa Teste")
                .cnpj("12345678910123")
                .contato("numero Teste")
                .endereco("Rua Teste, 123, Bairro Teste, Cidade Teste, Estado Teste")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        mapper.atualizarCliente(request, cliente);

        assertNotNull(cliente);

        assertEquals(request.cnpj(), cliente.getCnpj());
        assertEquals(request.razaoSocial(), cliente.getRazaoSocial());
        assertEquals(request.contato(), cliente.getContato());
        assertEquals(request.endereco(), cliente.getEndereco());
    }
}