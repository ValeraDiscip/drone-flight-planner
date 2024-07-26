package org.example.dao;

import org.example.dto.client.Parameter;

public interface ClientDao {
    Parameter getParameterByClientId(Integer clientId);
}
