package org.dev.securityapiserverbungee.policies;

import java.util.function.Supplier;
import org.dev.securityapiserverbungee.security.Token;

public interface TokenSupplier extends Supplier {

    @Override
    Token get();
}
