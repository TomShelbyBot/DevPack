package me.theseems.tomshelby.devpack.config;

import java.util.List;
import java.util.Objects;

public class DevConfig {
  private List<Long> developerIds;

  public DevConfig(List<Long> developerIds) {
    this.developerIds = developerIds;
  }

  public List<Long> getDeveloperIds() {
    return developerIds;
  }

  public void setDeveloperIds(List<Long> developerIds) {
    this.developerIds = developerIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DevConfig devConfig = (DevConfig) o;
    return developerIds.equals(devConfig.developerIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(developerIds);
  }

  @Override
  public String toString() {
    return "DevConfig{" +
        "developerIds=" + developerIds +
        '}';
  }
}
