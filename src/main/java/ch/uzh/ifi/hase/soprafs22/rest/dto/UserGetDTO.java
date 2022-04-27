package ch.uzh.ifi.hase.soprafs22.rest.dto;

import java.util.ArrayList;

import antlr.collections.List;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Game;

public class UserGetDTO {

  private Long id;
  private String username;
  private UserStatus status;
  // private ArrayList<Game> games;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }


  /* public ArrayList<Game> getGames() {
    return this.games;
  }

  public void setGames(ArrayList<Game> games) {
    this.games = games;
  } */

}
