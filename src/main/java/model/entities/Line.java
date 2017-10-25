package model.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Mohamed T. KASSAR
 *
 */

@Entity
@Table(name = "LINE")
public class Line implements Serializable {

	public static enum LineType {
		rer, tramway, metro, bus, transilien
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "LINE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int lineId;
	
	//TODO: Line name unique, dao dont add if it already exists
	@Column(name = "LINE_NAME")
	private String lineName;

	@Column(name = "LINE_TYPE")
	private LineType lineType;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "schedule")
	private Set<ScheduledLine> lineSchedules;

	public Set<ScheduledLine> getlineSchedules() {
		// return lineSchedules.stream().map(ls ->
		// ls.getSchedule()).collect(Collectors.toList());
		return lineSchedules;
	}

	public int getLineId() {
		return lineId;
	}

	@Enumerated(EnumType.STRING)
	public LineType getLineType() {
		return lineType;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineId(int id) {
		this.lineId = id;
	}

	public void setLineName(String lineId) {
		this.lineName = lineId;
	}

	public void setLineType(LineType lineType) {
		this.lineType = lineType;
	}

	@Override
	public String toString() {
		return "Line{" +
				"lineId=" + lineId +
				", lineName='" + lineName + '\'' +
				", lineType=" + lineType +
				", lineSchedules=" + lineSchedules +
				'}';
	}
}