package br.com.ismyburguer.pagamento.adapter.repository;

import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoModelToPagamentoConverter;
import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ConsultarPagamentoRepositoryImplTest {

    private ConsultarPagamentoRepositoryImpl repository;
    private PagamentoRepository pagamentoRepository;
    private PagamentoModelToPagamentoConverter converter;

    @BeforeEach
    void setUp() {
        pagamentoRepository = mock(PagamentoRepository.class);
        converter = mock(PagamentoModelToPagamentoConverter.class);
        repository = new ConsultarPagamentoRepositoryImpl(pagamentoRepository, converter);
    }

    @Test
    void deveConsultarPagamentoComSucesso() {
        // Arrange
        String pagamentoId = "123e4567-e89b-12d3-a456-556642440000";
        PagamentoModel pagamentoModel = new PagamentoModel();
        Pagamento pagamento = new Pagamento();

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pagamentoModel));
        when(converter.convert(pagamentoModel)).thenReturn(pagamento);

        // Act
        Pagamento result = repository.consultar(pagamentoId);

        // Assert
        assertNotNull(result);
        verify(pagamentoRepository, times(1)).findById(any(UUID.class));
        verify(converter, times(1)).convert(pagamentoModel);
    }

    @Test
    void deveLancarEntityNotFoundExceptionQuandoPagamentoNaoEncontrado() {
        // Arrange
        String pagamentoId = "123e4567-e89b-12d3-a456-556642440000";

        when(pagamentoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> repository.consultar(pagamentoId));
        verify(pagamentoRepository, times(1)).findById(any(UUID.class));
        verify(converter, never()).convert(any());
    }
}
