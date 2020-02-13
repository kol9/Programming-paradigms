map_get([(K, V)| _], K, V).
map_get([(HK, HV) | T], K, V) :-
		(HK < K), map_get(T, K, V).

map_put([], K, V,[(K, V)]).
map_put([(K, V) | T], K, NV, [(K, NV) | T]).
map_put([(HK, HV) | T], K, V, R) :-
		(		HK < K
		-> 	map_put(T, K, V, NH),
				append([(HK, HV)], NH, R)
				;
				HK > K
		->	append([(K, V), (HK, HV)],T, R)
		).

map_remove([],K,[]).
map_remove([(K, V) | T], K, T).
map_remove([(HK, HV) | T],K, R) :-
		(		HK < K
		-> 	map_remove(T, K, NT),
				append([(HK, HV)], NT, R)
				;
				HK > K
		->	append([(HK, HV)], T, R)
		).




map_floorKey([(K1, _)], K, K1) :-
		(K >= K1).

map_floorKey([(K1,_),(K2,_)| _ ],K,R) :-
		(K1 =< K), (K2 > K), R = K1.

map_floorKey([(K1,_) |T],K,R) :-
		map_floorKey(T,K,R).




%map_floorKey([(-9,i),(-2,b),(8,uk)],9,V).


