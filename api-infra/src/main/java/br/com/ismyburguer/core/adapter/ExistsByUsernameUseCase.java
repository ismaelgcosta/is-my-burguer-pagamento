package br.com.ismyburguer.core.adapter;

import java.util.UUID;

public interface ExistsByUsernameUseCase<T> {

    boolean existsByUsername(String username);
}
