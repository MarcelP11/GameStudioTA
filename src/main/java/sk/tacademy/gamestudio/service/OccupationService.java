package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Occupation;

import java.util.List;

public interface OccupationService {
    void addOccupation(Occupation occupation);

    List<Occupation> getOccupations();
}
