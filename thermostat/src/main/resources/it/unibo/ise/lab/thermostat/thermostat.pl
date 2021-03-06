:- dynamic(done/1).

warm_range(20, 30).
done(no).

start :-
    repeat,
    sleep(100 /* ms */), % just to slow down the execution
    warm_range(Min, Max),
    keep_temperature(Min, Max),
    done(yes).

keep_temperature(Min, Max) :-
    check_temperature(T),
    handle_temperature(T, Min, Max).

check_temperature(T) :-
    read_text("/path/to/environment.txt", T),
    write("Temperature is "), write(T), write('.'), nl.

handle_temperature(T, Min, _) :- T =< Min, !,
    T1 is T + 1,
    write_text("/path/to/environment.txt", T1),
    write("Pushing hot air."), nl.

handle_temperature(T, _, Max) :- T >= Max, !,
    T1 is T - 1,
    write_text("/path/to/environment.txt", T1),
    write("Pushing cold air."), nl.

handle_temperature(_, _, _) :-
    update(done(yes)),
    write("I'm done."), nl.
