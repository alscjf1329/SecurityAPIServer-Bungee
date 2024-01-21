package org.dev.securityapiserverbungee.policies;

import java.util.function.Supplier;

public interface VerificationCodeSupplier extends Supplier {

    @Override
    String get();
}
