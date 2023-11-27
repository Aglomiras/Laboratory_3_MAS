package org.example.Version_1.Model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegConfig {
    @XmlElement
    private int first_agent;
    @XmlElement
    private int last_agent;
    @XmlElementWrapper(name = "nodes")
    @XmlElement(name = "node")
    private List<Nodes> nodeList;
}
