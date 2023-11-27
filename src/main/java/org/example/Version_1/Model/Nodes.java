package org.example.Version_1.Model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Nodes {
    @XmlElement(name = "position")
    private int position;
    @XmlElementWrapper(name = "neighbours")
    @XmlElement(name = "neighbour")
    private List<Neighbour> neighbourList;
}
