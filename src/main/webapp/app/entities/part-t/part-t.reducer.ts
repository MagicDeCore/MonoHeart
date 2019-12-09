import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPartT, defaultValue } from 'app/shared/model/part-t.model';

export const ACTION_TYPES = {
  FETCH_PARTT_LIST: 'partT/FETCH_PARTT_LIST',
  FETCH_PARTT: 'partT/FETCH_PARTT',
  CREATE_PARTT: 'partT/CREATE_PARTT',
  UPDATE_PARTT: 'partT/UPDATE_PARTT',
  DELETE_PARTT: 'partT/DELETE_PARTT',
  RESET: 'partT/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPartT>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PartTState = Readonly<typeof initialState>;

// Reducer

export default (state: PartTState = initialState, action): PartTState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PARTT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARTT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARTT):
    case REQUEST(ACTION_TYPES.UPDATE_PARTT):
    case REQUEST(ACTION_TYPES.DELETE_PARTT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PARTT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARTT):
    case FAILURE(ACTION_TYPES.CREATE_PARTT):
    case FAILURE(ACTION_TYPES.UPDATE_PARTT):
    case FAILURE(ACTION_TYPES.DELETE_PARTT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARTT):
    case SUCCESS(ACTION_TYPES.UPDATE_PARTT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARTT):
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

const apiUrl = 'api/part-ts';

// Actions

export const getEntities: ICrudGetAllAction<IPartT> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARTT_LIST,
    payload: axios.get<IPartT>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPartT> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARTT,
    payload: axios.get<IPartT>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPartT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARTT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPartT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARTT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPartT> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARTT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
