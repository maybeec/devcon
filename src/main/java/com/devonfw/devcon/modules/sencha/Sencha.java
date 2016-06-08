package com.devonfw.devcon.modules.sencha;

import org.apache.commons.lang3.SystemUtils;

import com.devonfw.devcon.common.api.annotations.CmdModuleRegistry;
import com.devonfw.devcon.common.api.annotations.Command;
import com.devonfw.devcon.common.api.data.ProjectInfo;
import com.devonfw.devcon.common.api.data.ProjectType;
import com.devonfw.devcon.common.impl.AbstractCommandHolder;
import com.google.common.base.Optional;

/**
 *
 * @author ivanderk
 */
@CmdModuleRegistry(name = "sencha", description = "Sencha related commands", context = "global", deprecated = false)
public class Sencha extends AbstractCommandHolder {

  @SuppressWarnings("javadoc")
  @Command(name = "run", help = "compiles in DEBUG mode and then runs the internal Sencha web server (\"app watch\")")
  public void run() {

    // TODO Test on MacOSX & Unix
    if (!SystemUtils.IS_OS_WINDOWS) {
      getOutput().showMessage("Currently only supported on Windows");
      return;
    }
    Optional<ProjectInfo> project = getContextPathInfo().getProjectRoot();
    if (project.isPresent() && project.get().getProjecType().equals(ProjectType.Devon4Sencha)) {

      getOutput().showMessage("Sencha starting");

      Process p;
      try {
        p = Runtime.getRuntime().exec("cmd /c start sencha app watch", null, project.get().getPath().toFile());
        p.waitFor();
      } catch (Exception e) {

        getOutput().showError("An error occured during executing Sencha Cmd");
      }

    } else {
      getOutput().showMessage("Not a Sencha project (or does not have a corresponding devon.json file)");
    }
  }
}