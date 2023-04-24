package code.util.commands;

import basemod.devcommands.ConsoleCommand;
import code.ModFile;
import code.util.BrewStand;

public class ResetAndFillPouch extends ConsoleCommand {
    public ResetAndFillPouch() {
        requiresPlayer = true;
    }

    @Override
    protected void execute(String[] strings, int i) {
        BrewStand.resetPouchWithAllHerbs(ModFile.herbPouch);
    }
}
