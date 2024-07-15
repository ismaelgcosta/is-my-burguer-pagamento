package br.com.ismyburguer.pagamento.adapter.repository;

import br.com.ismyburguer.pagamento.adapter.converter.PagamentoToPagamentoModelConverter;
import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SalvarPagamentoRepositoryImplTest {

    private SalvarPagamentoRepositoryImpl repository;
    private PagamentoRepository pagamentoRepository;
    private PagamentoToPagamentoModelConverter converter;

    @BeforeEach
    void setUp() {
        pagamentoRepository = mock(PagamentoRepository.class);
        converter = mock(PagamentoToPagamentoModelConverter.class);
        repository = new SalvarPagamentoRepositoryImpl(pagamentoRepository, converter);
    }

    @Test
    void devePagarComSucesso() {
        // Arrange
        Pagamento pagamento = new Pagamento();
        PagamentoModel pagamentoModel = spy(new PagamentoModel());
        UUID pagamentoId = UUID.randomUUID();

        when(converter.convert(pagamento)).thenReturn(pagamentoModel);
        when(pagamentoRepository.save(pagamentoModel)).thenReturn(pagamentoModel);
        when(pagamentoModel.getPagamentoId()).thenReturn(pagamentoId);

        // Act
        UUID result = repository.salvar(pagamento);

        // Assert
        assertNotNull(result);
        assertEquals(pagamentoId, result);
        verify(converter, times(1)).convert(pagamento);
        verify(pagamentoRepository, times(1)).save(pagamentoModel);
    }
}
