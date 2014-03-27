package org.apache.cassandra.db.index.stratio.schema;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A {@link CellMapper} to map a double field.
 * 
 * @author adelapena
 */
public class CellMapperDouble extends CellMapper<Double> {

	private Float DEFAULT_BOOST = 1.0f;

	@JsonProperty("boost")
	private final Float boost;

	@JsonCreator
	public CellMapperDouble(@JsonProperty("boost") Float boost) {
		super();
		this.boost = boost == null ? DEFAULT_BOOST : boost;
	}

	@Override
	public Analyzer analyzer() {
		return EMPTY_ANALYZER;
	}

	@Override
	public Double indexValue(Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Number) {
			return ((Number) value).doubleValue();
		} else if (value instanceof String) {
			return Double.valueOf(value.toString());
		} else {
			throw new IllegalArgumentException(String.format("Value '%s' cannot be cast to Double", value));
		}
	}

	@Override
	public Double queryValue(Object value) {
		return indexValue(value);
	}

	@Override
	public Field field(String name, Object value) {
		Double number = indexValue(value);
		Field field = new DoubleField(name, number, STORE);
		field.setBoost(boost);
		return field;
	}

	@Override
	public Class<Double> baseClass() {
		return Double.class;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(" []");
		return builder.toString();
	}

}