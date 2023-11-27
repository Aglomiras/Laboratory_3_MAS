package org.example.Version_2.Behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class NodeForwardTransferBehaviours extends Behaviour {
    private List<String> neighborsAgent;
    private List<String> weightNeighborsAgent;
    private MessageTemplate messageTemplate;

    public NodeForwardTransferBehaviours(List<String> neighborsAgent, List<String> weightNeighborsAgent) {
        this.neighborsAgent = neighborsAgent;
        this.weightNeighborsAgent = weightNeighborsAgent;
    }

    @Override
    public void onStart() {
        this.messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    }

    @Override
    public void action() {
        ACLMessage receiv = myAgent.receive(messageTemplate);

        String[] contArr;
        if (receiv != null) {
            contArr = receiv.getContent().split(",");
            if (contArr[1].equals(getAgent().getLocalName())) {
//                log.info("Node found!");
                messagAgree(contArr);

            } else if (contArr[0].indexOf(getAgent().getLocalName()) == -1) {
                for (int i = 0; i < neighborsAgent.size(); i++) {
                    messageInfo(contArr, neighborsAgent.get(i), weightNeighborsAgent.get(i));
                }
            } else {
//                log.info("Dead end found!");
                messageProp(contArr);

            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    public void messagAgree(String[] contArr) {
        String[] path = parsPath(contArr);
        String path2 = parsPathCont(contArr);

        ACLMessage message1 = new ACLMessage(ACLMessage.AGREE);
        message1.setContent(path2 + "," + contArr[0] + getAgent().getLocalName() + "," + contArr[2]);
        message1.addReceiver(new AID(path[path.length - 1], false));
        getAgent().send(message1);
    }

    public void messageProp(String[] contArr) {
        String[] path = parsPath(contArr);
        String path2 = parsPathCont(contArr);

        ACLMessage message3 = new ACLMessage(ACLMessage.PROPOSE);
        message3.setContent(path2 + "," + contArr[0] + getAgent().getLocalName() + "," + contArr[2]);
        message3.addReceiver(new AID(path[path.length - 1], false));
        getAgent().send(message3);
    }

    public void messageInfo(String[] contArr, String neighborsAgent, String weightNeighborsAgent) {
        ACLMessage message2 = new ACLMessage(ACLMessage.INFORM);
        double val = Double.parseDouble(contArr[2]) + Double.parseDouble(weightNeighborsAgent);
        message2.setContent(contArr[0] + getAgent().getLocalName() + "|" + "," + contArr[1] + "," + val);
        message2.addReceiver(new AID(neighborsAgent, false));
        getAgent().send(message2);
    }

    public String[] parsPath(String[] contArr) {
        String[] path = contArr[0].split("\\|");
        return path;
    }

    public String parsPathCont(String[] contArr) {
        String[] path = parsPath(contArr);
        String[] path1 = Arrays.stream(Arrays.copyOfRange(path, 0, path.length - 1)).toArray(String[]::new);
        String path2 = String.join("|", path1);
        return path2;
    }
}
