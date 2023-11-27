package org.example.Version_1.Behaviuors;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class BehInitiator extends OneShotBehaviour {
    private String first;
    private String last;
    private List<String> neighborsAgent;
    private List<String> weightNeighborsAgent;

    public BehInitiator(String first, String last, List<String> neighborsAgent, List<String> weightNeighborsAgent) {
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
    }

    @Override
    public void action() {
        MessageTemplate messageTemplate1 = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
        MessageTemplate messageTemplate2 = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);

        double minWeight = 0;
        int countFind = 0;
        String minPath = "";

        int countTypik = 0;

        long start = System.currentTimeMillis();
        long end = start + 10 * 1000;
        while (System.currentTimeMillis() < end) {
            ACLMessage receive = myAgent.receive();

            if (receive != null) {
                if (messageTemplate1.match(receive)) {
                    countFind++;
                    String[] sumWeight = receive.getContent().split(",");
//                    System.out.println(Arrays.toString(sumWeight));
                    if (countFind == 1) {
                        minWeight = Double.parseDouble(sumWeight[2]);
                        minPath = sumWeight[1];
                    }
                    if (Double.parseDouble(sumWeight[2]) < minWeight) {
                        minWeight = Double.parseDouble(sumWeight[2]);
                        minPath = sumWeight[1];
                    }
                }
                if (messageTemplate2.match(receive)) {
                    countTypik++;
                }
            } else {
                block();
            }
        }
        System.out.println("minWeigh: " + minWeight);
        System.out.println("minPath: " + minPath);
        System.out.println("countFind: " + countFind);
        System.out.println("countTypik: " + countTypik);
    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }
}
