package org.example.Version_2.Behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;

public class InitiatorBehaviour extends Behaviour {
    private String first;
    private String last;
    private List<String> neighborsAgent;
    private List<String> weightNeighborsAgent;
    private MessageTemplate messageTemplate;
    private double minWeight = 0;
    private String minPath;
    private int countFind = 0;
    private int countTypik = 0;

    public InitiatorBehaviour(String first, String last, List<String> neighborsAgent, List<String> weightNeighborsAgent) {
        this.first = first;
        this.last = last;
        this.neighborsAgent = neighborsAgent;
        this.weightNeighborsAgent = weightNeighborsAgent;
    }

    @Override
    public void onStart() {
        for (int i = 0; i < neighborsAgent.size(); i++) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.setContent(getAgent().getLocalName() + "|" + "," + last + "," + weightNeighborsAgent.get(i));
            message.addReceiver(new AID(neighborsAgent.get(i), false));
            getAgent().send(message);
        }
        this.messageTemplate = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.AGREE), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
    }

    @Override
    public void action() {
        ACLMessage receive = myAgent.receive();

        if (receive != null) {
            if (receive.getPerformative() == ACLMessage.AGREE) {
                this.countFind++;
                String[] sumWeight = receive.getContent().split(",");
//                    System.out.println(Arrays.toString(sumWeight));
                if (countFind == 1) {
                    this.minWeight = Double.parseDouble(sumWeight[2]);
                    this.minPath = sumWeight[1];
                }
                if (Double.parseDouble(sumWeight[2]) < minWeight) {
                    this.minWeight = Double.parseDouble(sumWeight[2]);
                    this.minPath = sumWeight[1];
                }
            }
            if (receive.getPerformative() == ACLMessage.PROPOSE) {
                this.countTypik++;
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public int onEnd() {
        System.out.println("minWeigh: " + minWeight);
        System.out.println("minPath: " + minPath);
        System.out.println("countFind: " + countFind);
        System.out.println("countTypik: " + countTypik);
        return super.onEnd();
    }
}
