package org.example.Version_2.Model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegConfigXml {
    @XmlElement
    private int first_agent;
    @XmlElement
    private int last_agent;
    @XmlElement
    private int position;
    @XmlElementWrapper(name = "neighbours")
    @XmlElement(name = "neighbour")
    private List<Neighbour> neighbourList;
}
