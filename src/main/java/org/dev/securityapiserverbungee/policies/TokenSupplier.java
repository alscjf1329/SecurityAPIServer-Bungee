package org.dev.securityapiserverbungee.policies;

import java.util.function.Supplier;
import org.dev.securityapiserverbungee.dto.TokenDTO;

public interface TokenSupplier extends Supplier {

    @Override
    TokenDTO get();
}
