package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private int id;

	@Column(name = "LINE_NAME")
	private String lineName;

	@Column(name = "LINE_TYPE")
	private LineType lineType;

	public int getId() {
		return id;
	}

	@Enumerated(EnumType.STRING)
	public LineType getLineType() {
		return lineType;
	}

	public String getLineName() {
		return lineName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLineName(String lineId) {
		this.lineName = lineId;
	}

	public void setLineType(LineType lineType) {
		this.lineType = lineType;
	}
}
