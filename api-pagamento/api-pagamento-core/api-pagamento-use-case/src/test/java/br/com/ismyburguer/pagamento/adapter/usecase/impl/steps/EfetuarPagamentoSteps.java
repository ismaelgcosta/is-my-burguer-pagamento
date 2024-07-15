package br.com.ismyburguer.pagamento.adapter.usecase.impl.steps;

import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.adapter.usecase.impl.EfetuarPagamentoUseCaseImpl;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.SalvarPagamentoRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EfetuarPagamentoSteps {

    private SalvarPagamentoRepository repositoryMock;
    private EfetuarPagamentoUseCase useCase;
    private Validator validator;
    private Pagamento pagamento;
    private UUID pagamentoIdEsperado;
    private UUID pagamentoIdRetornado;

    @Before
    public void setUp() {
        repositoryMock = mock(SalvarPagamentoRepository.class);
        useCase = new EfetuarPagamentoUseCaseImpl(repositoryMock);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Given("um pagamento válido")
    public void um_pagamento_valido() {
        pagamento = spy(EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pagamento.class));
        pagamentoIdEsperado = UUID.randomUUID();
        when(repositoryMock.salvar(pagamento)).thenReturn(pagamentoIdEsperado);
    }

    @When("o pagamento é efetuado")
    public void o_pagamento_e_efetuado() {
        pagamentoIdRetornado = useCase.pagar(pagamento);
    }

    @Then("o pagamento deve ser efetuado com sucesso")
    public void o_pagamento_deve_ser_efetuado_com_sucesso() {
        assertNotNull(pagamentoIdRetornado);
        assertEquals(pagamentoIdEsperado, pagamentoIdRetornado);
    }

    @Then("o status do pagamento deve ser PAGO")
    public void o_status_do_pagamento_deve_ser_pago() {
        assertSame(pagamento.getStatusPagamento(), Pagamento.StatusPagamento.PAGO);
    }

    @Then("o QR Code deve ser gerado")
    public void o_qr_code_deve_ser_gerado() {
        assertNotNull(pagamento.getQrCode());
        assertEquals("00020101021243650016COM.MERCADOLIBRE02013063638f1192a-5fd1-4180-a180-8bcae3556bc35204000053039865802BR5925IZABEL AAAA DE MELO6007BARUERI62070503***63040B6D", pagamento.getQrCode());
    }

}
