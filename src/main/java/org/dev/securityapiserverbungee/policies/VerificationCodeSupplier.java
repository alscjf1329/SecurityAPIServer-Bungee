package org.dev.securityapiserverbungee.policies;

import java.util.function.Supplier;
import org.dev.securityapiserverbungee.dto.TokenDTO;

public interface VerificationCodeSupplier extends Supplier {

    @Override
    String get();
}
