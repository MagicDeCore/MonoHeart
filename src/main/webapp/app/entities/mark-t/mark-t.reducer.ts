import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMarkT, defaultValue } from 'app/shared/model/mark-t.model';

export const ACTION_TYPES = {
  FETCH_MARKT_LIST: 'markT/FETCH_MARKT_LIST',
  FETCH_MARKT: 'markT/FETCH_MARKT',
  CREATE_MARKT: 'markT/CREATE_MARKT',
  UPDATE_MARKT: 'markT/UPDATE_MARKT',
  DELETE_MARKT: 'markT/DELETE_MARKT',
  RESET: 'markT/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMarkT>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MarkTState = Readonly<typeof initialState>;

// Reducer

export default (state: MarkTState = initialState, action): MarkTState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MARKT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MARKT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MARKT):
    case REQUEST(ACTION_TYPES.UPDATE_MARKT):
    case REQUEST(ACTION_TYPES.DELETE_MARKT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MARKT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MARKT):
    case FAILURE(ACTION_TYPES.CREATE_MARKT):
    case FAILURE(ACTION_TYPES.UPDATE_MARKT):
    case FAILURE(ACTION_TYPES.DELETE_MARKT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MARKT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_MARKT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MARKT):
    case SUCCESS(ACTION_TYPES.UPDATE_MARKT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MARKT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/mark-ts';

// Actions

export const getEntities: ICrudGetAllAction<IMarkT> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MARKT_LIST,
    payload: axios.get<IMarkT>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMarkT> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MARKT,
    payload: axios.get<IMarkT>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMarkT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MARKT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMarkT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MARKT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMarkT> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MARKT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
