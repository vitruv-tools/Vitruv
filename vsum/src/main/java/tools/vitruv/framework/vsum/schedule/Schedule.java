package tools.vitruv.framework.vsum.schedule;

import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.VitruviusChange;

import java.util.List;
import java.util.Map;

public record Schedule(Map<Integer, List<VitruviusChange<Uuid>>> schedule, List<Integer> originalChangeHashOrder) {}
