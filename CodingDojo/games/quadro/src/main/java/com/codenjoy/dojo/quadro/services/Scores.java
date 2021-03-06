package com.codenjoy.dojo.quadro.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.services.PlayerScores;
import com.codenjoy.dojo.services.settings.Parameter;
import com.codenjoy.dojo.services.settings.Settings;

public class Scores implements PlayerScores {

    private final Parameter<Integer> winScore;
    private final Parameter<Integer> looseScore;
    private final Parameter<Integer> drawScore;

    private volatile int score;

    public Scores(int startScore, Settings settings) {
        this.score = startScore;

        // вот тут мы на админке увидим два поля с подписями
        // и возожностью редактировать значение по умолчанию
        winScore = settings.addEditBox("Win score").type(Integer.class).def(10);
        looseScore = settings.addEditBox("Loose score").type(Integer.class).def(1);
        drawScore = settings.addEditBox("Draw score").type(Integer.class).def(3);
    }

    @Override
    public int clear() {
        return score = 0;
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public void event(Object event) {
        if (event.equals(Events.WIN))
            score += winScore.getValue();
        else if (event.equals(Events.LOOSE))
            score += looseScore.getValue();
        else if (event.equals(Events.DRAW))
            score+= drawScore.getValue();
        score = Math.max(0, score);
    }
}
