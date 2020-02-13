ins((K, V), nil, t(K, V, nil, nil)).
ins((K, V), t(XK, XV, L, R), t(RK, RV, P, Q)) :-
    (   K < XK                                    
    ->  ins((K, V), L, NT),
        ((RK, RV), P, Q) = ((XK, XV), NT, R)
        ;
        K > XK                           
    ->  ins((K, V), R, NT),
        ((RK, RV), P, Q) = ((XK, XV), L, NT)
        ;
        ((RK, RV), P, Q) = ((XK, XV), L, R)  
    ).


find((K, V), t(K, V, _, _)).
find((K, V), t(VK, VV, L, _)) :- K < VK, find((K, V), L).
find((K, V), t(VK, VV, _, R)) :- K > VK, find((K, V), R).


build([], T, T).
build([(NK, NV) | Rest], Root, Res) :-
    ins((NK, NV), Root, NewT),
    build(Rest, NewT, Res).


getMostLeft(t(K, _, nil, _), K).
getMostLeft(t(_, _, L, _), MostLeft) :- getMostLeft(L, MostLeft).


closest(K, t(VK, _, _, nil), VK):- (K >= VK).

closest(K, t(VK, _, _, R), Res) :-
		(K >= VK), getMostLeft(R, MostLeft), (K < MostLeft), Res = VK.
		
closest(K, t(VK,_, L, R), Res) :- 
		(K < VK), closest(K, L, Res);
		(K > VK), closest(K, R, Res).





tree_build(L, R) :-
		rnd_permutation(L, NL),
		build(NL, nil, R).

%tree_build(L, R) :-
%		build(L, nil, R).

map_get(T, K, V) :-
		find((K, V), T).


map_floorKey(T, K, Res) :-
		closest(K, T, Res).








% random shuffle
remove_at(X,[X | T], 1, T).
remove_at(X,[Y | T], K, [Y | Ys]) :- K > 1, 
   K1 is K - 1, remove_at(X, T, K1, Ys).
   
rnd_select(_, 0, []).
rnd_select(Xs, N, [X | Zs]) :- N > 0,
    length(Xs, L),
    rand_int(L, Res),
    I is Res + 1,
    remove_at(X, Xs, I, Ys),
    N1 is N - 1,
    rnd_select(Ys, N1, Zs).

rnd_permutation(L1, L2) :- length(L1, N), rnd_select(L1, N, L2).
