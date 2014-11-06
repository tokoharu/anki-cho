package roteLearningNote;

import java.util.ArrayList;

public class Tag {
	Tag parentTag;
	ArrayList<Tag> childTag = new ArrayList<Tag>();
	String name;
	Tag(String name) {
		this.name = name;
		System.out.println(name + " is special Tag. Because this is no parent tag.");
	}
	Tag(String name, Tag parentTag) {
		this.name = name;
		this.parentTag = parentTag;
	}
	void addChildTag(Tag tag) {
		childTag.add(tag);
	}
	public String toString (){
		return this.name;
	}
}
