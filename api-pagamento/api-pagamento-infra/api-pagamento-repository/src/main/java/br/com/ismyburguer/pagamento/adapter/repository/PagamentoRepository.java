package br.com.ismyburguer.pagamento.adapter.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceDriver;
import br.com.ismyburguer.pagamento.adapter.model.PagamentoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@PersistenceDriver
public interface PagamentoRepository extends MongoRepository<PagamentoModel, UUID> {

    List<PagamentoModel> findAllByPedidoId(UUID pedidoId);
}
