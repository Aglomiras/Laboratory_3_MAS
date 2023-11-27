package org.example.Version_1.Behaviuors;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class BehNode extends Behaviour {
    private List<String> neighborsAgent;
    private List<String> weightNeighborsAgent;

    public BehNode(List<String> neighborsAgent, List<String> weightNeighborsAgent) {
        this.neighborsAgent = neighborsAgent;
        this.weightNeighborsAgent = weightNeighborsAgent;
    }

    @Override
    public void action() {
        MessageTemplate messageTemplate1 = MessageTemplate.MatchPerformative(ACLMessage.INFORM); //Получение прямого сообщения
        MessageTemplate messageTemplate2 = MessageTemplate.MatchPerformative(ACLMessage.AGREE); //Получение обратного сообщения
        MessageTemplate messageTemplate3 = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE); //Получение сообщения о нахождении тупика

        ACLMessage message = myAgent.receive();

        String[] cont = new String[3];
        int count = 0;

        if (message != null) {
            if (messageTemplate1.match(message)) {
                cont = message.getContent().split(",");
                count++;
            }
            if (messageTemplate2.match(message)) {
                cont = message.getContent().split(",");
                String[] path = cont[0].split("\\|");
                String[] path1 = Arrays.stream(Arrays.copyOfRange(path, 0, path.length - 1)).toArray(String[]::new);
                String path2 = String.join("|", path1);

                ACLMessage message1 = new ACLMessage(ACLMessage.AGREE);
                message1.setContent(path2 + "," + cont[1] + "," + cont[2]);
                message1.addReceiver(new AID(path[path.length - 1], false));
                getAgent().send(message1);
            }
            if (messageTemplate3.match(message)) {
                cont = message.getContent().split(",");
                String[] path = cont[0].split("\\|");
                String[] path1 = Arrays.stream(Arrays.copyOfRange(path, 0, path.length - 1)).toArray(String[]::new);
                String path2 = String.join("|", path1);

                ACLMessage message2 = new ACLMessage(ACLMessage.PROPOSE);
                message2.setContent(path2 + "," + cont[1] + "," + cont[2]);
                message2.addReceiver(new AID(path[path.length - 1], false));
                getAgent().send(message2);
            }
        } else {
            block();
        }
        if (count == 1) {
            /**
             * Нахождение последнего (искомого) агента
             * */
            if (cont[1].equals(getAgent().getLocalName())) {
//                log.info("Node found!");
                String[] path = cont[0].split("\\|");
                String[] path1 = Arrays.stream(Arrays.copyOfRange(path, 0, path.length - 1)).toArray(String[]::new);
                String path2 = String.join("|", path1);
//                log.info("Node found! " + cont[0] + " " + cont[2]);
                ACLMessage message12 = new ACLMessage(ACLMessage.AGREE);
                message12.setContent(path2 + "," + cont[0] + getAgent().getLocalName() + "," + cont[2]);
                message12.addReceiver(new AID(path[path.length - 1], false));
                getAgent().send(message12);

            } else if (cont[0].indexOf(getAgent().getLocalName()) == -1) { //Условие означает, что такого агента еще не было в цепочке передачи сообщения
                for (int i = 0; i < neighborsAgent.size(); i++) {
                    ACLMessage message13 = new ACLMessage(ACLMessage.INFORM);
//                    System.out.println(cont[2] + " " + weightNeighborsAgent.get(i));
                    double val = Double.parseDouble(cont[2]) + Double.parseDouble(weightNeighborsAgent.get(i));
                    message13.setContent(cont[0] + getAgent().getLocalName() + "|" + "," + cont[1] + "," + val);
                    message13.addReceiver(new AID(neighborsAgent.get(i), false));
                    getAgent().send(message13);
                }

            } else { //Оставшееся условие означает, что мы либо зациклились в сети, либо попали в тупик
//                log.info("Dead end found!");
                String[] path = cont[0].split("\\|");
                String[] path1 = Arrays.stream(Arrays.copyOfRange(path, 0, path.length - 1)).toArray(String[]::new);
                String path2 = String.join("|", path1);

                ACLMessage message14 = new ACLMessage(ACLMessage.PROPOSE);
                message14.setContent(path2 + "," + cont[0] + getAgent().getLocalName() + "," + cont[2]);
                message14.addReceiver(new AID(path[path.length - 1], false));
                getAgent().send(message14);
            }
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
