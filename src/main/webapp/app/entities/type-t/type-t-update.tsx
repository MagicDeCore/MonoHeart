import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IModelT } from 'app/shared/model/model-t.model';
import { getEntities as getModelTs } from 'app/entities/model-t/model-t.reducer';
import { IMarkT } from 'app/shared/model/mark-t.model';
import { getEntities as getMarkTs } from 'app/entities/mark-t/mark-t.reducer';
import { getEntity, updateEntity, createEntity, reset } from './type-t.reducer';
import { ITypeT } from 'app/shared/model/type-t.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITypeTUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITypeTUpdateState {
  isNew: boolean;
  idsmodelT: any[];
  markTId: string;
}

export class TypeTUpdate extends React.Component<ITypeTUpdateProps, ITypeTUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsmodelT: [],
      markTId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getModelTs();
    this.props.getMarkTs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { typeTEntity } = this.props;
      const entity = {
        ...typeTEntity,
        ...values,
        modelTS: mapIdList(values.modelTS)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/type-t');
  };

  render() {
    const { typeTEntity, modelTS, markTS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="monoHeartApp.typeT.home.createOrEditLabel">Create or edit a TypeT</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : typeTEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="type-t-id">ID</Label>
                    <AvInput id="type-t-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="type-t-name">
                    Name
                  </Label>
                  <AvField id="type-t-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="type-t-modelT">Model T</Label>
                  <AvInput
                    id="type-t-modelT"
                    type="select"
                    multiple
                    className="form-control"
                    name="modelTS"
                    value={typeTEntity.modelTS && typeTEntity.modelTS.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {modelTS
                      ? modelTS.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/type-t" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  modelTS: storeState.modelT.entities,
  markTS: storeState.markT.entities,
  typeTEntity: storeState.typeT.entity,
  loading: storeState.typeT.loading,
  updating: storeState.typeT.updating,
  updateSuccess: storeState.typeT.updateSuccess
});

const mapDispatchToProps = {
  getModelTs,
  getMarkTs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TypeTUpdate);
