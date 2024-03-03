package controller;

import domain.BooleanGenerator;
import domain.Height;
import domain.Ladder;
import domain.Players;
import domain.PrizeResults;
import domain.Prizes;
import domain.Width;
import java.util.Map;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class LadderGameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final BooleanGenerator booleanGenerator;

    public LadderGameController(InputView inputView, OutputView outputView, BooleanGenerator booleanGenerator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.booleanGenerator = booleanGenerator;
    }

    public void run() {
        Players players = initPlayers();
        Width width = new Width(players.getSize());
        Prizes prizes = initPrizes(width);
        Height height = initHeight();
        Ladder ladder = initLadder(width, height);
        PrizeResults prizeResults = new PrizeResults(ladder.getResult(players,prizes));
        outputView.printResult(ladder.getLinesInformation(), players.getNames(), prizes.getPrizeNames());
        viewingUntilGetAll(prizeResults);
    }

    private Players initPlayers() {
        return repeatUntilValidInput(() -> new Players(inputView.readPlayerNames()));
    }

    private Prizes initPrizes(Width width) {
        return repeatUntilValidInput(() -> Prizes.of(inputView.readPrizeNames(), width));
    }

    private Height initHeight() {
        return repeatUntilValidInput(() -> new Height(inputView.readHeight()));
    }

    private Ladder initLadder(Width width, Height height) {
        return Ladder.of(width, height, booleanGenerator);
    }
    private void viewingUntilGetAll(PrizeResults prizeResults) {
        String operator;
        do {
            operator = repeatUntilValidInput(() -> viewLadderResult(prizeResults));
        } while (!operator.equals(PrizeResults.GET_ALL_RESULT_OPERATOR));
    }

    private String viewLadderResult(PrizeResults prizeResults) {
        String operator = repeatUntilValidInput(inputView::readPlayerForResultViewing);
        Map<String, String> results = prizeResults.convertResultToData(prizeResults.getByOperator(operator));
        outputView.printPrizeResult(results);
        return operator;
    }

    private <R> R repeatUntilValidInput(Supplier<R> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return repeatUntilValidInput(supplier);
        }
    }
}
