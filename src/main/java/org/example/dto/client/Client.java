package org.example.dto.client;

import lombok.Data;

import java.util.List;

@Data
public class Client {
    private int id;
    private Parameter parameters;
    private List<Flight> flights;
}
