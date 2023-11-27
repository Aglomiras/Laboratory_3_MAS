package org.example.Version_2.Behaviours;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class WaitInitBehaviour extends ParallelBehaviour {
    private Behaviour initiatorBehaviour;
    private Behaviour wakeupBeh;
    private String first;
    private String last;
    private List<String> neighbors;
    private List<String> weight;

    public WaitInitBehaviour(String findFirstAgent, String findLastAgent, List<String> findNeighborsAgents, List<String> findWeightNeighborsAgents) {
        super(WHEN_ANY);
        this.first = findFirstAgent;
        this.last = findLastAgent;
        this.neighbors = findNeighborsAgents;
        this.weight = findWeightNeighborsAgents;
    }

    @Override
    public void onStart() {
        initiatorBehaviour = new InitiatorBehaviour(first, last, neighbors, weight);
        wakeupBeh = new WakerBehaviour(myAgent, 5000) {
            boolean wake = false;

            @Override
            protected void onWake() {
                wake = true;
                log.info("TIME IS UP!!!");
            }

            @Override
            public int onEnd() {
                return wake ? 0 : 1;
            }
        };
        this.addSubBehaviour(initiatorBehaviour);
        this.addSubBehaviour(wakeupBeh);
    }

    @Override
    public int onEnd() {
        initiatorBehaviour.onEnd();
        log.info("{} The initiator stopped waiting!");
        return super.onEnd();
    }
}
