import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IModelT, defaultValue } from 'app/shared/model/model-t.model';

export const ACTION_TYPES = {
  FETCH_MODELT_LIST: 'modelT/FETCH_MODELT_LIST',
  FETCH_MODELT: 'modelT/FETCH_MODELT',
  CREATE_MODELT: 'modelT/CREATE_MODELT',
  UPDATE_MODELT: 'modelT/UPDATE_MODELT',
  DELETE_MODELT: 'modelT/DELETE_MODELT',
  RESET: 'modelT/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IModelT>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ModelTState = Readonly<typeof initialState>;

// Reducer

export default (state: ModelTState = initialState, action): ModelTState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MODELT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MODELT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MODELT):
    case REQUEST(ACTION_TYPES.UPDATE_MODELT):
    case REQUEST(ACTION_TYPES.DELETE_MODELT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MODELT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MODELT):
    case FAILURE(ACTION_TYPES.CREATE_MODELT):
    case FAILURE(ACTION_TYPES.UPDATE_MODELT):
    case FAILURE(ACTION_TYPES.DELETE_MODELT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MODELT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_MODELT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MODELT):
    case SUCCESS(ACTION_TYPES.UPDATE_MODELT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MODELT):
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

const apiUrl = 'api/model-ts';

// Actions

export const getEntities: ICrudGetAllAction<IModelT> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MODELT_LIST,
    payload: axios.get<IModelT>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IModelT> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MODELT,
    payload: axios.get<IModelT>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IModelT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MODELT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IModelT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MODELT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IModelT> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MODELT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
