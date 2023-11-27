package org.example.Version_2.Behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Arrays;

public class NodeBackTransferBehaviour extends Behaviour {
    private MessageTemplate messageTemplate;

    @Override
    public void onStart() {
        this.messageTemplate = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.AGREE), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
    }

    @Override
    public void action() {
        ACLMessage receiv = myAgent.receive(messageTemplate);

        String[] contArr;

        if (receiv != null) {
            contArr = receiv.getContent().split(",");
            if (receiv.getPerformative() == ACLMessage.AGREE) {
                messagAgree(contArr);
            }
            if (receiv.getPerformative() == ACLMessage.PROPOSE) {
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

    public void messagAgree(String[] contArr) {
        String[] path = parsPath(contArr);
        String path2 = parsPathCont(contArr);

        ACLMessage message1 = new ACLMessage(ACLMessage.AGREE);
        message1.setContent(path2 + "," + contArr[1] + "," + contArr[2]);
        message1.addReceiver(new AID(path[path.length - 1], false));
        getAgent().send(message1);
    }

    public void messageProp(String[] contArr) {
        String[] path = parsPath(contArr);
        String path2 = parsPathCont(contArr);

        ACLMessage message3 = new ACLMessage(ACLMessage.PROPOSE);
        message3.setContent(path2 + "," + contArr[1] + "," + contArr[2]);
        message3.addReceiver(new AID(path[path.length - 1], false));
        getAgent().send(message3);
    }
}
