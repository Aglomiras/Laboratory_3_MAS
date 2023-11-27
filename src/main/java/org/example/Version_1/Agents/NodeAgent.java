package org.example.Version_1.Agents;

import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;
import org.example.Version_1.Behaviuors.BehNode;
import org.example.Version_1.Behaviuors.BehInitiator;
import org.example.Version_1.Supported.AgentService;
import org.example.Version_1.Supported.ParserXml;

import java.util.List;

@Slf4j
public class NodeAgent extends Agent {
    @Override
    protected void setup() {
        log.info(this.getName() + " was born");
        AgentService.registrAgent(this, "Service");

        String ag = "Agent";
        String pos = this.getLocalName().substring(ag.length());

        ParserXml parserXml = new ParserXml();
        String first = parserXml.findFirstAgent();
        String last = parserXml.findLastAgent();
        List<String> neighborsAgent = parserXml.findNeighborsAgents(Integer.parseInt(pos) - 1);
        List<String> weightNeighborsAgent = parserXml.findWeightNeighborsAgents(Integer.parseInt(pos) - 1);

        if (first.equals(this.getLocalName())) {
            log.info("Initiator - " + this.getLocalName());
            addBehaviour(new BehInitiator(first, last, neighborsAgent, weightNeighborsAgent));
        } else {
            log.info("Node loser - " + this.getLocalName());
            addBehaviour(new BehNode(neighborsAgent, weightNeighborsAgent));
        }
    }
}
