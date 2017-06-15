package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "place_type")
public class PlaceTypeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2734029527598763116L;
	@Id
	@Column(name = "place_type_id")
	private int placeTypeId;
	@Column(name = "place_type_name")
	private String placeTypeName;
	
	public void setPlaceTypeId(int placeTypeId) {
		this.placeTypeId = placeTypeId;
	}
	public int getPlaceTypeId() {
		return placeTypeId;
	}
	public void setPlaceTypeName(String placeTypeName) {
		this.placeTypeName = placeTypeName;
	}
	public String getPlaceTypeName() {
		return placeTypeName;
	}
}
