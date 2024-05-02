package unigearrent.unigearrentjava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import unigearrent.unigearrentjava.models.CarPost;
import unigearrent.unigearrentjava.models.TrailerPost;
import unigearrent.unigearrentjava.repositories.ICarRepository;
import unigearrent.unigearrentjava.repositories.ITrailerRepository;
import unigearrent.unigearrentjava.services.repositoryservices.CarService;
import unigearrent.unigearrentjava.services.repositoryservices.TrailerService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarAndTrailerTests {
    @Mock
    private ICarRepository carRepository;
    @InjectMocks
    private CarService carService;

    @Mock
    private ITrailerRepository trailerRepository;
    @InjectMocks
    private TrailerService trailerService;

    @Test
    public void CarAndTrailerServices_GetByIdReturnsCorrectObject(){
        CarPost car1 = new CarPost();
        car1.setId(1);
        CarPost car2 = new CarPost();
        car2.setId(2);

        when(carRepository.findById(2)).thenReturn(Optional.of(car2));

        Assertions.assertEquals(car2, carService.GetById(2).get());

        TrailerPost trailer1 = new TrailerPost();
        trailer1.setId(3);
        TrailerPost trailer2 = new TrailerPost();
        trailer2.setId(4);

        when(trailerRepository.findById(3)).thenReturn(Optional.of(trailer1));

        Assertions.assertEquals(trailer1, trailerService.GetById(3).get());
    }

    @Test
    public void CarAndTrailerServices_UpdateObjects(){
        CarPost toBeUpdated = new CarPost();
        CarPost updater = new CarPost();

        toBeUpdated.setId(1);
        toBeUpdated.setName("OldName");
        updater.setId(1);
        updater.setName("NewName");
        when(carRepository.findById(1)).thenReturn(Optional.of(toBeUpdated));
        when(carRepository.save(updater)).thenAnswer((i) -> {
            CarPost updaterArgument = i.getArgument(0);
            toBeUpdated.setName(updaterArgument.getName());
            return null;
        });
        carService.Update(updater);
        Assertions.assertEquals(updater.getName(), toBeUpdated.getName());

        TrailerPost toBeUpdatedTrailer = new TrailerPost();
        TrailerPost updaterTrailer = new TrailerPost();

        toBeUpdatedTrailer.setId(2);
        toBeUpdatedTrailer.setName("OldName");
        updaterTrailer.setId(2);
        updaterTrailer.setName("NewNameTrailer");
        when(trailerRepository.findById(2)).thenReturn(Optional.of(toBeUpdatedTrailer));
        when(trailerRepository.save(updaterTrailer)).thenAnswer((i) -> {
            TrailerPost updaterTrailerArgument = i.getArgument(0);
            toBeUpdatedTrailer.setName(updaterTrailerArgument.getName());
            return null;
        });
        trailerService.Update(updaterTrailer);
        Assertions.assertEquals(updaterTrailer.getName(), toBeUpdatedTrailer.getName());
    }
}
