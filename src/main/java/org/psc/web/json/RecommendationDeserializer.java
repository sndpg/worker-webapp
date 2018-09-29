package org.psc.web.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.psc.web.domain.Recommendation;
import org.psc.web.domain.RecommendationResult;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class RecommendationDeserializer extends JsonDeserializer<Recommendation> {

	public RecommendationDeserializer() {
	}

	@Override
	public Recommendation deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = p.getCodec().readTree(p);

		Recommendation recommendation = new Recommendation();
		Map<String, RecommendationResult> recommendationsByMotiveWorld = new HashMap<>();

		JsonNode recommendations = node.get("recommendations");
		Iterator<Entry<String, JsonNode>> recommendationsMap = recommendations.fields();
		while (recommendationsMap.hasNext()) {
			Entry<String, JsonNode> recEntry = recommendationsMap.next();

			Iterator<JsonNode> it = recEntry.getValue().elements();
			Iterable<JsonNode> iterable = () -> it;
/*
			List<Object> cNodes = new ArrayList<>();
			for (JsonNode cNode : iterable) {
				cNodes.add(cNode);
			}
			Object[] results = cNodes.toArray(new Object[cNodes.size()]);
*/
			
			Stream<JsonNode> recommendationsStream = StreamSupport.stream(iterable.spliterator(), false);
			Object[] results = recommendationsStream.toArray(Object[]::new);

			/*
			 * p.setCurrentValue(recEntry.getValue()); Object[] results =
			 * p.readValueAs(Object[].class);
			 */

			RecommendationResult recResult = new RecommendationResult().withResult(results)
					.withInjectedId("I'm injected");
			recommendationsByMotiveWorld.put(recEntry.getKey(), recResult);
		}
		recommendation.setInfo(node.get("info").asText());
		recommendation.setRecommendations(recommendationsByMotiveWorld);

		return recommendation;
	}

}
