package org.example.Version_1.Supported;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AgentService {
    /**
     * Класс для регистрации агентов на сервисе.
     * <p>
     * !!! Метод registrAgent:
     * Входные параметры:
     * - agent - агент;
     * - serviceName - имя сервиса (строковый тип);
     * Что делает: регистрирует агенты на определенном сервисе (заданном).
     * <p>
     * !!! Метод findAgents:
     * Входные параметры:
     * - agent - агент;
     * - serviceName - имя сервиса (строковый тип);
     * Что делает: находит агента на каком-либо сервисе (заданном).
     */
    public static void registrAgent(Agent agent, String serviceName) {
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(agent.getAID());

        ServiceDescription description = new ServiceDescription();
        description.setType(serviceName);
        description.setName(agent.getLocalName());
        dfad.addServices(description);

        try {
            DFService.register(agent, dfad);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<AID> findAgents(Agent agent, String serviceType) {
        DFAgentDescription dfad = new DFAgentDescription();
        ServiceDescription description = new ServiceDescription();
        description.setType(serviceType);
        dfad.addServices(description);

        try {
            DFAgentDescription[] search = DFService.search(agent, dfad);
            return Arrays.stream(search)
                    .map(DFAgentDescription::getName)
                    .collect(Collectors.toList());
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }
}
