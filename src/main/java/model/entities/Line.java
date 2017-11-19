package model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Mohamed T. KASSAR
 */

@Entity
@Table(name = "LINE", uniqueConstraints = @UniqueConstraint(columnNames = {"LINE_NAME", "LINE_TYPE"}))
public class Line implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "LINE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int lineId;
    @Column(name = "LINE_NAME")
    private String lineName;
    @Column(name = "LINE_TYPE")
    private LineType lineType;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "line")
    private Set<ClientScheduledLine> lineSchedules;

    public Set<ClientScheduledLine> getlineSchedules() {
        // return lineSchedules.stream().map(ls ->
        // ls.getSchedule()).collect(Collectors.toList());
        return lineSchedules;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int id) {
        this.lineId = id;
    }

    @Enumerated(EnumType.STRING)
    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineId) {
        this.lineName = lineId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Line) {
            Line temp = (Line) obj;
            return lineName.equals(temp.lineName) && lineType.equals(temp.lineType);
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" + "\"lineId\":" + lineId + ", \"lineName\":\"" + lineName + '\"' + ", \"lineType\":\"" + lineType + '\"'
                + '}';
    }

    public static enum LineType {
        rer, tramway, metro, bus, transilien
    }
}
