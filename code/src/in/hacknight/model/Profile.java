package in.hacknight.model;

public class Profile {
	public String name = null;
	public int property = 0;
	public int duration = 0;
	public boolean isAddNewRow = false;

	public Profile(String name, int property, int duration, boolean _isAddNewRow) {
		this.name = name;
		this.property = property;
		this.duration = duration;
		this.isAddNewRow = _isAddNewRow;
	}

}
