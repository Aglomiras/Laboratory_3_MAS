package org.example.Version_2.Agents;

import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;
import org.example.Version_2.Behaviours.NodeBackTransferBehaviour;
import org.example.Version_2.Behaviours.NodeForwardTransferBehaviours;
import org.example.Version_2.Behaviours.WaitInitBehaviour;
import org.example.Version_2.Supported.ParserXml;

@Slf4j
public class NodeAgent extends Agent {
    @Override
    protected void setup() {
        log.info("{} Was born " + this.getLocalName());

        ParserXml parserXml = new ParserXml("src/main/java/org/example/Version_2/Resources/" + this.getLocalName() + ".xml");

        if (!parserXml.findFirstAgent().equals("Agent0")) {
            addBehaviour(new WaitInitBehaviour(
                    parserXml.findFirstAgent(),
                    parserXml.findLastAgent(),
                    parserXml.findNeighborsAgents(),
                    parserXml.findWeightNeighborsAgents()));
        } else {
            addBehaviour(new NodeForwardTransferBehaviours(parserXml.findNeighborsAgents(), parserXml.findWeightNeighborsAgents()));
            addBehaviour(new NodeBackTransferBehaviour());
        }
    }
}
