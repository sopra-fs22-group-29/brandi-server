package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Card;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.websocket.Move;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CardDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.GameGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserAndGamesGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.MoveGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.websocket.MovePostDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "games", ignore = true)
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "status", target = "status")
    UserGetDTO convertEntityToUserGetDTO(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "games", target = "games")
    UserAndGamesGetDTO convertEntityToUserAndGamesGetDTO(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "gameOver", target = "gameOver")
    @Mapping(source = "gameOn", target = "gameOn")
    @Mapping(source = "roundsPlayed", target = "roundsPlayed")
    @Mapping(source = "playerStates", target = "playerStates")
    @Mapping(source = "boardstate", target = "boardstate")
    GameGetDTO convertEntityToGameGetDTO(Game createdGame);


    @Mapping(source = "ballId", target = "ballId")
    @Mapping(source = "destinationTile", target = "destinationTile")
    @Mapping(source = "targetBallId", target = "targetBallId")
    @Mapping(source = "targetBallNewPosition", target = "targetBallNewPosition")
    @Mapping(source = "holesTravelled", target = "holesTravelled")
    @Mapping(source = "ballIdsEliminated", target = "ballIdsEliminated")
    @Mapping(source = "newPositions", target = "newPositions")
    @Mapping(target = "cardId", ignore = true)
    MoveGetDTO convertEntityToMoveGetDTO(Move Move);

    @Mapping(source = "ballId", target = "ballId")
    @Mapping(source = "destinationTile", target = "destinationTile")
    @Mapping(source = "playedCard", target = "playedCard")
    @Mapping(source = "index", target = "index")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "targetBallId", ignore = true)
    @Mapping(target = "holesTravelled", ignore = true)
    @Mapping(target = "targetBallNewPosition", ignore = true)
    Move convertMovePostDTOtoEntity(MovePostDTO Move);


    @Mapping(source = "rank", target = "rank")
    @Mapping(source = "suit", target = "suit")
    @Mapping(target = "index", ignore = true)
    CardDTO convertEntityToCardDTO(Card Card);

    @Mapping(source = "rank", target = "rank")
    @Mapping(source = "suit", target = "suit")
    Card convertCardDTOToEntity(CardDTO cardDTO);
}
