package org.example.Version_2.Model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Neighbour {
    @XmlElement(name = "neigPositNode")
    private int position;
    @XmlElement(name = "weight")
    private int weight;
}
