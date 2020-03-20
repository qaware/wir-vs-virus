package de.qaware.mercury.mercury.business.impl;

import de.qaware.mercury.mercury.business.UUIDFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class UUIDFactoryImpl implements UUIDFactory {
    @Override
    public UUID create() {
        return UUID.randomUUID();
    }
}