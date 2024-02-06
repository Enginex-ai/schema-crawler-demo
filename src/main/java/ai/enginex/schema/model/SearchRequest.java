package ai.enginex.schema.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchRequest {
	private String searchText;

	private Integer page;
	private Integer size;

}
